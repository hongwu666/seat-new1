import com.maizuo.seat.dao.BasePreloadAble;
import com.maizuo.seat.dao.BaseSystemRedisDao;
import com.maizuo.seat.entity.Foretell;

public class a {

	public static void main(String[] args) {
		BasePreloadAble able = new BaseSystemRedisDao();
		able.reload();
	}
}
