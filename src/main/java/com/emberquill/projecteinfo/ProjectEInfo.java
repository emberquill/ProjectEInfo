package com.emberquill.projecteinfo;

import com.emberquill.projecteinfo.network.RemoteDataMessage;
import com.emberquill.projecteinfo.network.ResponseMessage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ProjectEInfo.MOD_ID, name = ProjectEInfo.NAME, version = ProjectEInfo.VERSION)
public class ProjectEInfo
{
    public static final String MOD_ID = "projecteinfo";
    public static final String NAME = "ProjectE Info";
    public static final String VERSION = "0.1";

    public static final Logger logger = LogManager.getLogger(MOD_ID);

    public static SimpleNetworkWrapper network;
    public static NBTTagCompound cachedData = new NBTTagCompound();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        network.registerMessage(ResponseMessage.ResponseHandler.class, ResponseMessage.class, 0, Side.CLIENT);
        network.registerMessage(RemoteDataMessage.Handler.class, RemoteDataMessage.class, 1, Side.SERVER);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT) {
            return;
        }

        if (Loader.isModLoaded("projecte")) {
            TagProjectE.register();
        }
    }
}
