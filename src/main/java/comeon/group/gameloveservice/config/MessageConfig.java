package comeon.group.gameloveservice.config;

import comeon.group.gameloveservice.util.MapperHelper;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import static comeon.group.gameloveservice.util.MapperHelper.getOrDefault;

/**
 * @author Behrooz Mohamadi
 * @email behroooz.mohamadi@gmail.com
 * @date 3/27/2018 11:42 AM
 */
@Component
@AllArgsConstructor
public class MessageConfig {
    final MessageSource messageSource;

    public String getMessage(String code, String... args) {
        return MapperHelper.getOrDefault(() -> messageSource.getMessage(code, args, LocaleContextHolder.getLocale()), code, NoSuchMessageException.class);
    }
}
