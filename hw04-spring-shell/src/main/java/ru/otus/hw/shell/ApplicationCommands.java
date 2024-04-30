package ru.otus.hw.shell;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.security.LoginContext;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent(value = "Application Commands")
@RequiredArgsConstructor
public class ApplicationCommands {

    private final LoginContext loginContext;

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Run Application", key = {"s", "r", "start", "run"})
    @ShellMethodAvailability(value = "isApplicationCommandAvailable")
    public void startApplication() {
        testRunnerService.run();
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Anonymous") String userName) {
        loginContext.login(userName);
        return String.format("Добро пожаловать: %s", userName);
    }



    private Availability isApplicationCommandAvailable() {
        return loginContext.isUserLoggedIn()
                ? Availability.available()
                : Availability.unavailable("Сначала залогиньтесь");
    }
}
