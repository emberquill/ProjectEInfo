package com.emberquill.projecteinfo.network;

import com.emberquill.projecteinfo.ProjectEInfo;
import io.netty.buffer.ByteBuf;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.api.capabilities.IKnowledgeProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public class RemoteDataMessage implements IMessage {

    public RemoteDataMessage() {}

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<RemoteDataMessage, ResponseMessage> {

        public Handler() {}

        @Override
        public ResponseMessage onMessage(RemoteDataMessage message, MessageContext ctx) {
            final NBTTagCompound data = new NBTTagCompound();
            final EntityPlayerMP player = ctx.getServerHandler().player;
            IThreadListener mainThread = player.getServerWorld();
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        IKnowledgeProvider knowledgeProvider = player.getCapability(ProjectEAPI.KNOWLEDGE_CAPABILITY, null);
                        data.setLong("EMC", knowledgeProvider.getEmc());
                        ResponseMessage response = new ResponseMessage(data);
                        ProjectEInfo.network.sendTo(response, player);
                    } catch (Exception e) {
                        ProjectEInfo.logger.error(e);
                    }
                }
            });
            return null;
        }
    }
}
