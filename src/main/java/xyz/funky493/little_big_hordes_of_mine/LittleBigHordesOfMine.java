package xyz.funky493.little_big_hordes_of_mine;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.funky493.little_big_hordes_of_mine.command.LittleBigHordesOfMineCommand;
import xyz.funky493.little_big_hordes_of_mine.util.InputUtil;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LittleBigHordesOfMine implements ModInitializer {
    public static final String MODID = "little_big_hordes_of_mine";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static LoadedData loadedData = new LoadedData();
    public static LittleBigHordesOfMineConfig config;
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        AutoConfig.register(LittleBigHordesOfMineConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(LittleBigHordesOfMineConfig.class).getConfig();
        registerReloadListener();
        LittleBigHordesOfMineCommand.register();
    }

    public static void reloadConfig() {
        AutoConfig.getConfigHolder(LittleBigHordesOfMineConfig.class).load();
        config = AutoConfig.getConfigHolder(LittleBigHordesOfMineConfig.class).getConfig();
    }

    private static void registerReloadListener() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier(MODID, "resource_listener");
            }

            @Override
            public void reload(ResourceManager manager) {
                LOGGER.info("Loading resource jsons...");
                for(Map.Entry<Identifier, Resource> id : manager.findResources("lbhom", path -> path.toString().endsWith(".json")).entrySet()) {
                    try(InputStream stream = manager.getResource(id.getKey()).orElseThrow().getInputStream()) {
                        Identifier location = fixIdentifier(id.getKey());
                        JsonElement json = JsonParser.parseString(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
                        String type = getFolderType(id.getKey());
                        LOGGER.info("Loading resource json " + location + " of type " + type);
                        switch(type) {
                            case "wave":
                                loadedData.loadWave(json, location);
                                break;
                            case "participant":
                                loadedData.loadParticipant(json, location);
                                break;
                            default:
                                LOGGER.warn("Unknown resource json type " + type + " for " + id.getKey());
                        }

                    } catch(Exception e) {
                        LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
                    }
                }
            }

            private String getFolderType(Identifier id) {
                String[] path = id.getPath().split("/");
                return path[path.length - 2];
            }

            private static Identifier fixIdentifier(Identifier id) {
                return new Identifier(id.getNamespace(), id.getPath().replace(".json", "").replaceFirst("lbhom/.*/", ""));
            }
        });
    }
}
