/**
 * @Author ZhiFei
 * @Date 2021-07-06 22:14
 *
 * 线程通信的应用：经典例题：生产者/消费者问题
 *
 * 生产者(Productor)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品，
 * 店员一次只能持有固定数量的产品(比如:20），如果生产者试图生产更多的产品，
 * 店员会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；
 * 如果店中没有产品了，店员会告诉消费者等一下，
 * 如果店中有产品了再通知消费者来取走产品。
 *
 * 分析：
 *      1.是否是多线程的问题？是，生产者的线程，消费者的线程
 *      2.是否有共享数据的问题？是，店员、产品、产品数
 *      3.如何解决线程的安全问题？同步机制，有三种方法
 *      4.是否涉及线程的通信？是
 **/
class Clerk{

    private int produceCount = 0;  // 产品数量

    public synchronized void productProduce() {  // 同步监视器：this
        if (produceCount < 20){
            produceCount++;
            System.out.println(Thread.currentThread().getName() + ": 开始生产第" + produceCount + "个产品");

            // 生产出一个产品之后，就可以唤醒所有消费者线程，让他们开始消费
            notifyAll();

        }else{
            // 产品超过20个，生产者线程阻塞，wait
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // 同步监视器：this (同👆相同，为同一把锁，当上面生产者获得锁后，消费者就需要等待锁)
    public synchronized void consumeProduce() {
        if (produceCount > 0){
            System.out.println(Thread.currentThread().getName() + ": 开始消费第" + produceCount + "个产品");
            produceCount--;

            // 消费者线程消费了一个产品后，产品数小于20，可以唤醒生产者线程去生产产品了
            notify();

        }else{
            // 产品少于1个，消费者线程阻塞，wait
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Productor extends Thread{ // 生产者线程
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": 开始生产产品");
        while (true){  // 一直生产
            // 慢点生产
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.productProduce();
        }
    }
}

class Consumer extends Thread{  // 消费者线程
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": 开始消费产品");
        while (true){ // 一直消费
            // 慢点消费
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.consumeProduce();
        }
    }
}

public class ProductConsumeTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor p = new Productor(clerk); // 线程1:生产者1
        p.setName("生产者1");
        Consumer c1 = new Consumer(clerk); // 线程2：消费者1
        c1.setName("消费者1");
        Consumer c2 = new Consumer(clerk); // 线程2：消费者2
        c2.setName("消费者2");

        p.start();
        c1.start();
        c2.start();
    }
}
