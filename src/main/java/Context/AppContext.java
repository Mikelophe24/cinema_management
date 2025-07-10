package Context;

import java.time.LocalDateTime;

import Enum.AccountEnum;
import Model.Account;

public class AppContext {
	public static Account currentAccount = new Account(14, "test1", "1", AccountEnum.Role.CUSTOMER,
			AccountEnum.Status.ACTIVE, "k", "sssss", LocalDateTime.now(), LocalDateTime.now());
}
