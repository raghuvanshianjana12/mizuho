import com.order.Order;
import com.order.OrderBook;
import com.order.OrderKey;
import com.order.Side;

public class TestOrderBook {
    public static void main(String[] args) {

        OrderBook orderBook = new OrderBook();
        Order o1 = new Order(new OrderKey(1L),10L,100.0, Side.BID);
        Order o2 = new Order(new OrderKey(2L),20L,110.0, Side.BID);
        Order o3 = new Order(new OrderKey(3L),20L,110.0, Side.BID);
        Order o4 = new Order(new OrderKey(4L),30L,120.0, Side.BID); // Higher

        Order o5 = new Order(new OrderKey(5L),10L,125.0, Side.ASK);
        Order o6 = new Order(new OrderKey(6L),20L,135.0, Side.ASK);
        Order o7 = new Order(new OrderKey(7L),20L,135.0, Side.ASK);
        Order o8 = new Order(new OrderKey(8L),30L,145.0, Side.ASK); // Lower

        orderBook.addOrder(o1);
        orderBook.addOrder(o2);
        orderBook.addOrder(o3);
        orderBook.addOrder(o4);
        orderBook.addOrder(o5);
        orderBook.addOrder(o6);
        orderBook.addOrder(o7);
        orderBook.addOrder(o8);

        orderBook.removeOrder(new OrderKey(3L));
        orderBook.removeOrder(new OrderKey(2L));
        orderBook.updateOrder(new OrderKey(1L), 40L);

        System.out.println("Best Ask -->" + orderBook.getBestPrice(Side.ASK));
        System.out.println("Best Bid -->" + orderBook.getBestPrice(Side.BID));

        System.out.println("getPriceBySideAndLevel -->" + orderBook.getPriceBySideAndLevel(Side.ASK,2));
        System.out.println("getPriceBySideAndLevel -->" + orderBook.getPriceBySideAndLevel(Side.BID,2));

        System.out.println("getTotalSizeBySideAndLevel -->" + orderBook.getTotalSizeBySideAndLevel(Side.ASK,2));
        System.out.println("getTotalSizeBySideAndLevel -->" + orderBook.getTotalSizeBySideAndLevel(Side.BID,2));

    }
}