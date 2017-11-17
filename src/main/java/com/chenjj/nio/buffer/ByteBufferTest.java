package com.chenjj.nio.buffer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ByteBufferTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        // 0 <= mark <= position <= limit <= capacity
        // 填充
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

        // 获取
        // System.out.println((char) buffer.get());// 当前的position是6,对应的是空字符
        // 当前get()(相对函数)被调用时，position在返回时前进1。
        // System.out.println("position: " + buffer.position());// 7
        // System.out.println((char) buffer.get(0));// M
        // get(index)(绝对函数)并不会影响position
        // System.out.println("position: " + buffer.position());// 7
        // System.out.println((char) buffer.get(9));// 对应的是空字符
        // System.out.println((char) buffer.get(10));// java.lang.IndexOutOfBoundsException

        // 翻转
        // buffer.limit(buffer.position()).position(0);
        // 这句和上面的语句等效
        buffer.flip();
        System.out.println("position: " + buffer.position());// 0
        // 上界属性指明了缓冲区有效内容的末端
        System.out.println("limit: " + buffer.limit());// 6
        // java.lang.IndexOutOfBoundsException
        // System.out.println(buffer.get(6));
    }
}
