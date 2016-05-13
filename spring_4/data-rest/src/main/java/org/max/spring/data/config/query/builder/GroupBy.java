package org.max.spring.data.config.query.builder;


import org.max.spring.data.config.query.builder.limitspec.LimitSpec;

/**
 * Created by Maksym_Bondarenko on 4/20/2016.
 */
public class GroupBy extends Query {

    LimitSpec limitSpec;

    public GroupBy() {
        queryType = Type.groupBy;
    }

    public LimitSpec getLimitSpec() {
        return limitSpec;
    }

    public void setLimitSpec(LimitSpec limitSpec) {
        this.limitSpec = limitSpec;
    }
}
