package com.emberquill.projecteinfo;

import com.emberquill.projecteinfo.network.RemoteDataMessage;
import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import moze_intel.projecte.api.ProjectEAPI;
import net.minecraft.entity.player.EntityPlayer;

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
                if (world.isRemote) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 500 || delay < 0) {
                        ProjectEInfo.network.sendToServer(new RemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return String.valueOf(ProjectEInfo.cachedData.getTag("EMC"));
                } else {
                    return String.valueOf(Objects.requireNonNull(player.getCapability(ProjectEAPI.KNOWLEDGE_CAPABILITY, null)).getEmc());
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
