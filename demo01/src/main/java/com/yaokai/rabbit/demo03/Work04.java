package com.yaokai.rabbit.demo03;


import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yaokai.rabbit.utils.RabbitMqUtils;

/**
 * 消费者01
 *
 * @author zhiyuan
 */
public class Work04 {
    private static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1 等待接收消息处理时间较 长");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("接收到消息:" + message);
            //int i = 1 / 0;
            /**
             * 1.消息标记 tag
             * 2.是否批量应答未应答消息
             */
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            int i = 1 / 0;
        };

        CancelCallback cancelCallback = (s) -> {
            System.out.println(s + "消费者取消消费接口回调逻辑");
        };

        //采用手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, cancelCallback);


    }
}
