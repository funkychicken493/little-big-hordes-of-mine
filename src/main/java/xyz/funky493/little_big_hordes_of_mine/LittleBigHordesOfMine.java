package xyz.funky493.little_big_hordes_of_mine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.funky493.little_big_hordes_of_mine.datapack.LoadedData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
                        LOGGER.info("Loading resource json " + id.getKey() + " of type " + getFolderType(id.getKey()));
                        LOGGER.info(new String(stream.readAllBytes(), Charset.defaultCharset()));
                        if(!conditionsMet(stream)){
                            LOGGER.info("Conditions not met for " + id.getKey() + ", so it will not be loaded.");
                            continue;
                        }

                        switch(getFolderType(id.getKey())) {
                            case "horde":
                                loadedData.loadHorde(stream);
                                break;
                            case "wave":
                                loadedData.loadWave(new String(stream.readAllBytes(), Charset.defaultCharset()), id.getKey());
                                break;
                            case "participant":
                                loadedData.loadParticipant(stream, id.getKey());
                                break;
                            default:
                                LOGGER.warn("Unknown resource json type " + getFolderType(id.getKey()) + " for " + id.getKey());
                                break;
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

            private boolean conditionsMet(InputStream stream) {
                Gson gson = new GsonBuilder().create();
                return ResourceConditions.objectMatchesConditions(gson.fromJson(new InputStreamReader(stream), JsonObject.class));
            }
        });
    }
}
