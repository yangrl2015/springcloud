package com.study.springboot.configuration;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class WindowsOrUnixValueCondition extends AnyNestedCondition{

    public WindowsOrUnixValueCondition(ConfigurationPhase configurationPhase) {
        super(configurationPhase);
    }
    @ConditionalOnProperty()
    static class WindowsValue{

    }

}
