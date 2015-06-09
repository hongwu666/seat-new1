import java.util.List;

import org.junit.Test;

import com.maizuo.seat.dao.CinemaDao;
import com.maizuo.seat.dao.cache.CinemaDaoCache;
import com.maizuo.seat.dao.cache.UserDaoCache;
import com.maizuo.seat.entity.User;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.service.offer.RequestObj;
import com.maizuo.seat.service.offer.impl.FirePhenixServiceImpl;
import com.maizuo.seat.service.offer.impl.SkyStarServiceImpl;
import com.maizuo.seat.util.FastJsonUtils;
import com.maizuo.seat.util.JedisUtils;

public class ServiceTest {
	// @Test
	public void test() {
		FirePhenixServiceImpl impl = new FirePhenixServiceImpl();
		List<CinemaOffer> list = impl.getCinemas();
		System.out.println(list);
	}

	private CinemaDao cinemaDao = new CinemaDaoCache();

	@Test
	public void a() {

		List<String> list = JedisUtils.getMapValues("User_obj");
		String j = JedisUtils.getFieldFromObject("User_obj", "1");
		System.out.println(j);
		User uu=FastJsonUtils.getBean(j, User.class);
		System.out.println(uu);
		List<User> u = FastJsonUtils.toList(list, User.class);
		String list1 = JedisUtils.getFieldFromObject("User_list", "1");
		System.out.println(list1);
		System.out.println(FastJsonUtils.getBeans(list1, User.class).get(0).getAge());
		System.out.println(u);
		System.out.println(list);
		
		UserDaoCache userDaoCache=new UserDaoCache();
		System.out.println(userDaoCache.get(1));
		System.out.println(userDaoCache.getList());
		System.out.println(userDaoCache.getList("1").get(1).getName());
	}

	// @Test
	public void testSeat() {
		FirePhenixServiceImpl impl = new FirePhenixServiceImpl();
		RequestObj bo = new RequestObj();
		bo.setOfferCinemaId("31051201");
		bo.setLinkId("492");
		bo.setHallId("2");
		List<SeatOffer> list = impl.getSeats(bo);
		System.out.println(list);
	}

	// @Test
	public void testSky() {
		SkyStarServiceImpl impl = new SkyStarServiceImpl();
		impl.getToken();
		impl.getCinemas();
		RequestObj bo = new RequestObj();
		bo.setOfferCinemaId("31051201");
		bo.setLinkId("492");
		bo.setHallId("2");
		List<SeatOffer> list = impl.getSeats(bo);
		System.out.println(list);
	}
}
