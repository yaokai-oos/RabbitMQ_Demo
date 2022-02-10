package com.yaokai.rabbit.demo04;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.yaokai.rabbit.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入信息:");
        while (scanner.hasNext()){
            String message = scanner.nextLine();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息" + message);
        }

    }

}
