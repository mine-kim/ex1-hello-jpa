package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;
        System.out.println(a == b);

        Address address1 = new Address("1","2","3");
        Address address2 = new Address("1","2","3");
        System.out.println(address1 == address2);
        System.out.println(address1.equals(address2));
    }
}
