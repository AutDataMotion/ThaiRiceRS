package thairice.mvc.t8message;

import com.jfinal.aop.Enhancer;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.platform.mvc.base.BaseService;
import org.apache.log4j.Logger;

import java.math.BigInteger;

public class T8messageService extends BaseService {

    private static Logger log = Logger.getLogger(T8messageService.class);

    public static final T8messageService service = Enhancer.enhance(T8messageService.class);

    public T8message SelectById(Integer id) {

        T8message mdl = T8message.dao.findFirst("select * from t8message where id=?", id);
        return mdl;
    }

    /**
     * 消息分页查询
     *
     * @param page  当前页
     * @param row   分页大小
     * @param type_ 消息类型
     * @return
     */
    public Page<T8message> selectByPage(int page, int row, String type_, String orderColumn, String orderDir) {
        StringBuilder sb = new StringBuilder();
        sb.append(" FROM t8message m");
        sb.append(" LEFT JOIN t3user u ON u.id=m.send_userid");
        sb.append(" WHERE 1=1");
        if (StrKit.notBlank(type_)) {
            sb.append(" AND m.type_='" + type_ + "'");
        }
        sb.append(" ORDER BY m." + orderColumn + " " + orderDir);
        return T8message.dao.paginate(page, row, "SELECT m.*,u.name_", sb.toString());
    }

    /**
     * 个人消息分页查询
     *
     * @param page
     * @param row
     * @param status
     * @param name
     * @param userId 用户id
     * @return
     */
    public Page<T8message> selectByUserPage(int page, int row, String status,String search, BigInteger userId) {
        String select = "SELECT m.type_,m.content,m.add_time,s.*";
        StringBuilder sb = new StringBuilder();
        sb.append(" FROM r4message_send s");
        sb.append(" LEFT JOIN t8message m ON m.id=s.message_id");
        sb.append(" LEFT JOIN t3user u ON u.id=s.receive_userid");
        sb.append(" WHERE 1=1");
        sb.append(" AND u.type_='01'");
        sb.append(" AND s.receive_userid=?");
        if (StrKit.notBlank(search)) {
            sb.append(" AND m.content LIKE '%" + search + "%'");
        }
        if (StrKit.notBlank(status)) {
            sb.append(" AND s.status_='" + status + "'");
        }
        sb.append(" ORDER BY s.id DESC");
        return T8message.dao.paginate(page, row, select, sb.toString(), userId);
    }

    /**
     * 删除单个或多个
     *
     * @param ids
     * @return
     */
    public int deletes(String ids) {
        int rows = Db.update(" DELETE FROM thairice.t8message WHERE id in (" + ids + ")");
        if (rows > 0) {
            //同时删除管理的用户消息
            for (String id : ids.split(",")) {
                Db.update(" DELETE FROM thairice.r4message_send where message_id=?", id);
            }
        }
        return rows;
    }
}
