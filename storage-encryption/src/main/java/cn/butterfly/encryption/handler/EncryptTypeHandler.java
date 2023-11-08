package cn.butterfly.encryption.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.UnaryOperator;
import cn.butterfly.encryption.util.AESUtils;
import cn.butterfly.encryption.util.ServletUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import javax.servlet.http.HttpServletRequest;

/**
 * 加密类型字段处理类
 *
 * @author zjw
 * @date 2023-10-27
 */
public class EncryptTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, handleResult(parameter, AESUtils::encrypt));
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return handleResult(rs.getString(columnName), AESUtils::decrypt);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return handleResult(rs.getString(columnIndex), AESUtils::decrypt);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return handleResult(cs.getString(columnIndex), AESUtils::decrypt);
    }
    
    /**
     * 值加解密处理
     *
	 * @param val 值
     * @param fun 处理函数
     * @return 结果
     */
    private String handleResult(String val, UnaryOperator<String> fun) {
        HttpServletRequest request = ServletUtils.getRequest();
        return StringUtils.isBlank(val) || !"/method4".equals(request.getServletPath()) ? val : fun.apply(val);
    }
    
}