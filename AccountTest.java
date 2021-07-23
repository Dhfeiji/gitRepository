/**
 * @Author ZhiFei
 * @Date 2021-07-06 17:24
 *
 * 银行有一个账户。
 *      有两个储户分别向同一个账户存3000元，每次存1000，存3次。 每次存完打印账户余额。
 *
 * 分析：
 *      1.是否是多线程问题？是，两个储户线程
 *      2.是否有共享数据？有，账户（或账户余额）
 *      3.是否有线程安全问题？有
 *      4.需要考虑如何解决线程安全问题？同步机制：有三种方式。
 *
 *      方法：继承Thread类，并同步方法
 **/
class Account{ // 账户
    private int balance; // 余额

    public Account(int balance){ // 构造器用于初始化余额为0
        this.balance = balance;
    }
    // 存钱
    public synchronized void deposit(int money){
        if (money > 0){ // money 为要存的钱
            balance += money;
            try {
                Thread.sleep(1000); // 加这个是为了验证此操作存在线程安全问题，需要加线程同步
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": 存款成功，余额为：" + balance);
        }
    }

}

class Customer extends Thread{ // 储户线程
    private Account account;

    public Customer(Account account){ // 构造器对储户进行初始化，此方法可以用于保证多个储户线程共用一个账户
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            // 线程调用账户中的存钱方法进行存钱
            account.deposit(1000);
        }
    }
}

public class AccountTest {
    public static void main(String[] args) {
        Account account = new Account(0);
        Customer c1 = new Customer(account); // 储户线程1
        Customer c2 = new Customer(account); // 储户线程2

        c1.setName("甲储户");
        c2.setName("乙储户");

        c1.start();
        c2.start();
    }
}
