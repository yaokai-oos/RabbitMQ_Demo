package com.yaokai.rabbit.demo04;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yaokai.rabbit.utils.RabbitMqUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class ReceiveLogs02 {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //申明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        /**
         *  生成一个临时的队列 队列的名称是随机的
         *  当消费者断开和该队列的连接时 队列自动删除
         */
        String queueName = channel.queueDeclare().getQueue();
        //把该临时队列绑定我们的exchange 其中routingkey(也称之为binding key)为空字符串
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("等待接收消息,把接收到的消息写到文件.....");
        //接收消息回调
        DeliverCallback deliverCallback = (consumerTag,deliver)->{
            String message = new String(deliver.getBody(),"UTF-8");
            File file = new File("C:\\zhongtiankeji\\RDM\\RDM_P12\\Application\\rdmapp\\power\\platform\\scripts\\log.txt");
            FileUtils.write(file,message+"\n",StandardCharsets.UTF_8,true);
            System.out.println("数据写入文件成功");
        };
        //消费者取消消费接口回调
        CancelCallback cancelCallback = deliver -> {
            System.out.println(deliver + "消费者取消消费接口回调逻辑");
        };
        channel.basicConsume(queueName,true,deliverCallback,cancelCallback);

    }

}
