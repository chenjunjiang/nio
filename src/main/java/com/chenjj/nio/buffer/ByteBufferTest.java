package com.chenjj.nio.buffer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ByteBufferTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        // 0 <= mark <= position <= limit <= capacity
        /**
         * 填充
         */
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o');
        System.out.println("position: " + buffer.position());// 5
        System.out.println("limit: " + buffer.limit());// 10
        System.out.println("capacity: " + buffer.capacity());// 10
        // 在不丢失位置的情况下进行一些更改，把缓冲区的内容从"Hello"更改为"Mellow"
        buffer.put(0, (byte) 'M').put((byte) 'w');
        System.out.println("position: " + buffer.position());// 6
        System.out.println("limit: " + buffer.limit());// 10
        System.out.println("capacity: " + buffer.capacity());// 10
        byte[] bytes = buffer.array();
        System.out.println(new String(bytes));// Mellow    ,w后面还有4个空字符,因为capacity是10

        /**
         * 获取
         */
        // System.out.println((char) buffer.get());// 当前的position是6,对应的是空字符
        // 当前get()(相对函数)被调用时，position在返回时前进1。
        // System.out.println("position: " + buffer.position());// 7
        // System.out.println((char) buffer.get(0));// M
        // get(index)(绝对函数)并不会影响position
        // System.out.println("position: " + buffer.position());// 7
        // System.out.println((char) buffer.get(9));// 对应的是空字符
        // System.out.println((char) buffer.get(10));// java.lang.IndexOutOfBoundsException

        /**
         * 翻转
         */
        // buffer.limit(buffer.position()).position(0);
        // 这句和上面的语句等效
        buffer.flip();
        System.out.println("position: " + buffer.position());// 0
        // 上界属性指明了缓冲区有效内容的末端
        System.out.println("limit: " + buffer.limit());// 6
        // java.lang.IndexOutOfBoundsException
        // System.out.println(buffer.get(6));
        // rewind()函数与flip()相似，但不影响上界。

        /**
         * 释放
         */
        byte[] byteArray = new byte[buffer.limit()];
        // hasRemaining()函数判断是否已经到达缓冲区的上界；这样循环很低效，因为每次都要判断
        /*for (int i = 0; buffer.hasRemaining(); i++) {
            byteArray[i] = buffer.get();
        }*/
        // 从当前位置到上界还剩余的元素数目
        int count = buffer.remaining();
        /*for (int i = 0; i < count; i++) {
            byteArray[i] = buffer.get();
        }
        System.out.println(new String(byteArray));// Mellow
        System.out.println(buffer.position());// 6
        System.out.println(buffer.limit());// 6
        System.out.println(buffer.capacity());// 10*/
        // buffer.put((byte) 'x'); // java.nio.BufferOverflowException

        // clear()函数并不会改变缓冲区中的任何数据元素，而是仅仅将上界设置为容量的值，并把position设置为0.
        /*buffer.clear();
        System.out.println(new String(byteArray));// Mellow
        System.out.println(buffer.position());// 0
        System.out.println(buffer.limit());// 10
        System.out.println(buffer.capacity());// 10*/

        /**
         * compact的作用是将 position 与 limit之间的数据复制到buffer的开始位置，
         * 复制后 position  = limit -position,limit = capacity,
         * 但如果position 与limit 之间没有数据的话发，就不会进行复制
         * http://blog.csdn.net/u014783753/article/details/43965647
         */
        buffer.get();
        buffer.get();
        System.out.println(buffer.position());// 2
        System.out.println(buffer.limit());// 6
        System.out.println(buffer.capacity());// 10
        buffer.compact();
        System.out.println("position: " + buffer.position());// 4
        System.out.println("limit: " + buffer.limit());// 10
        System.out.println("capacity: " + buffer.capacity());// 10
        bytes = buffer.array();
        System.out.println(new String(bytes));// llowow    ,w后面还有4个空字符,因为capacity是10
        // 再次put的时候就会覆盖最后的ow
        buffer.put((byte) 'x');
        buffer.put((byte) 'y');
        System.out.println("position: " + buffer.position());// 6
        bytes = buffer.array();
        System.out.println(new String(bytes));// llowxy    ,y后面还有4个空字符,因为capacity是10
    }
}
