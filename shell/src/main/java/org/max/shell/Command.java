package org.max.shell;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/*

* <p/>
* Created by : Maksym_Bondarenko
* Created at : 22-01-2016
* #
*/
@Component
public class Command implements CommandMarker {

    @CliCommand(value = "termo", help = "Some help text maybe.")
    public String get(@CliOption(key = {"","in"}) String text){
        return "Hello, " + text;

    }
}
