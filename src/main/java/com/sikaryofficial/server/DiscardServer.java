package com.sikaryofficial.server;

import com.sikaryofficial.handler.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author : wuweihong
 * @desc : TODO  请填写你的功能描述
 * @date : 2025-01-09
 */


public class DiscardServer {

 private int port;

 public DiscardServer(int port) {
     this.port = port;
 }

 public void run() throws Exception {
     EventLoopGroup bossGroup = new NioEventLoopGroup();
     EventLoopGroup workerGroup = new NioEventLoopGroup();
     try {
         ServerBootstrap b = new ServerBootstrap();
         b.group(bossGroup, workerGroup)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                  @Override
                  protected void initChannel(SocketChannel socketChannel) throws Exception {
                      socketChannel.pipeline().addLast(new DiscardServerHandler());
                  }
                 });
         ChannelFuture f = b.bind(port).sync();
         f.channel().closeFuture().sync();
     } finally {
         bossGroup.shutdownGracefully();
         workerGroup.shutdownGracefully();
     }
 }

}
