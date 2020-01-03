package com.git.hui.boot.jpacase.strategy;

import lombok.Setter;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Created by @author yihui in 17:25 19/12/30.
 */
public class JpaNamingStrategyStandardImpl extends PhysicalNamingStrategyStandardImpl {
    private static final long serialVersionUID = 3223639634029498079L;
    @Setter
    private static int mode = 0;

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        if (mode == 1) {
            if (name.isQuoted()) {
                return name;
            } else {
                return Identifier.toIdentifier("`" + name.getText() + "`", true);
            }
        } else {
            return name;
        }
    }
}
