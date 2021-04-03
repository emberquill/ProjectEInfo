package com.emberquill.projecteinfo;

import com.emberquill.projecteinfo.network.RemoteDataMessage;
import com.emberquill.projecteinfo.util.EMCFormat;
import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import moze_intel.projecte.api.ProjectEAPI;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

public abstract class TagProjectE extends Tag {

    @Override
    public String getCategory() {
        return "projecte";
    }

    protected static long lastRemoteUpdate = 0;

    public static class CurrentEMC extends TagProjectE {

        @Override
        public String getValue() {
            try {
                //TODO: Implement a proper Client/Server sided proxy to make this less hackish
                if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 500 || delay < 0) {
                        ProjectEInfo.network.sendToServer(new RemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return String.valueOf(ProjectEInfo.cachedData.getTag("EMC"));
                } else {
                    return EMCFormat.formatEMC(Objects.requireNonNull(player.getCapability(ProjectEAPI.KNOWLEDGE_CAPABILITY, null)).getEmc());
                }
            } catch (Throwable e) {
                ProjectEInfo.logger.error(e);
            }
            return "-1";
        }
    }

    public static void register() {
        TagRegistry.INSTANCE.register(new CurrentEMC().setName("peemc"));
    }
}
