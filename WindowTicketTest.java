/**
 * @Author ZhiFei
 * @Date 2021-07-03 15:14
 *
 * 例子：多窗口卖票（创建三个窗口卖票，总票数为100张）
 *      方式一：继承Thread方式创建线程
 *      存在线程安全问题，线程同步能解决
 **/


class Window extends Thread{

    private static int ticket = 100; // static修饰的属性变量，所有对象共用

    @Override
    public void run() {
        while (true){
            if (ticket > 0){
                // 线程类里面，getName() 等价于 Thread.currentThread().getName()
                System.out.println(getName() + ": 卖票，票号为: " + ticket); // ticket来表示票号
                ticket--;  // static修饰后，避免了三个窗口各卖100张(300张)。 此时三个窗口共卖100张。
            }else{
                System.out.println(getName() + ": 票量余额不足");
                break; // 跳出循环
            }
        }
    }
}
public class WindowTicketTest {
    public static void main(String[] args) {

        // 此时有线程不安全问题，比如三个窗口都在卖同一张票(线程同步部分能解决此问题)
        // Thread-0: 卖票，票号为: 100.  Thread-2: 卖票，票号为: 100.  Thread-1: 卖票，票号为: 100
        Window t1 = new Window();
        Window t2 = new Window();
        Window t3 = new Window();
        t1.start();
        t2.start();
        t3.start();
    }
}
