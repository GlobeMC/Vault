package net.milkbowl.vault.economy.plugins;

import me.raidordev.goldeconomy.GoldEconomy;
import me.raidordev.goldeconomy.account.Account;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.UUID;

public class Economy_GoldEconomy extends AbstractEconomy {

    private Plugin plugin;
    private GoldEconomy goldEconomy;

    public Economy_GoldEconomy(Plugin plugin) {
        this.plugin = plugin;

        Plugin ge = plugin.getServer().getPluginManager().getPlugin("GoldEconomy");

        if (ge != null && ge.isEnabled()) {
            goldEconomy = (GoldEconomy) ge;
        }
    }

    @Override
    public boolean isEnabled() {
        return goldEconomy != null && goldEconomy.getEconomyManager().isEconomyDisabled();
    }

    @Override
    public String getName() {
        return "Gold Economy";
    }

    @Override
    public double getBalance(String accountHolder) {
        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder);
        return accountOptional.map(Account::getBalance).orElse(0.0);
    }

    @Override
    public double getBalance(UUID accountHolder) {
        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder);
        return accountOptional.map(Account::getBalance).orElse(0.0);
    }

    @Override
    public double getBalance(OfflinePlayer accountHolder) {
        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder.getUniqueId());
        return accountOptional.map(Account::getBalance).orElse(0.0);
    }

    @Override
    public boolean has(String accountHolder, double amount) {
        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder);

        return accountOptional.isPresent() && accountOptional.get().getBalance() >= amount;
    }

    @Override
    public boolean has(UUID accountHolder, double amount) {
        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder);

        return accountOptional.isPresent() && accountOptional.get().getBalance() >= amount;
    }

    @Override
    public boolean has(OfflinePlayer accountHolder, double amount) {
        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder.getUniqueId());

        return accountOptional.isPresent() && accountOptional.get().getBalance() >= amount;
    }

    @Override
    public EconomyResponse withdraw(String accountHolder, double amount) {
        if (amount < 1)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Please enter an amount greater than or equal to 1.");

        double balance;
        EconomyResponse.ResponseType type;
        String errorMessage = null;

        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setBalance(account.getBalance() - amount);
            balance = account.getBalance();
            type = EconomyResponse.ResponseType.SUCCESS;
        } else {
            amount = 0;
            balance = 0;
            type = EconomyResponse.ResponseType.FAILURE;
            errorMessage = "That player could not be found.";
        }

        return new EconomyResponse(amount, balance, type, errorMessage);
    }

    @Override
    public EconomyResponse withdraw(UUID accountHolder, double amount) {
        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Please enter an amount greater than or equal to 1.");

        double balance;
        EconomyResponse.ResponseType type;
        String errorMessage = null;

        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setBalance(account.getBalance() - amount);
            balance = account.getBalance();
            type = EconomyResponse.ResponseType.SUCCESS;
        } else {
            amount = 0;
            balance = 0;
            type = EconomyResponse.ResponseType.FAILURE;
            errorMessage = "That player could not be found.";
        }

        return new EconomyResponse(amount, balance, type, errorMessage);
    }

    @Override
    public EconomyResponse withdraw(OfflinePlayer accountHolder, double amount) {
        if (amount < 0)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Please enter an amount greater than or equal to 1.");

        double balance;
        EconomyResponse.ResponseType type;
        String errorMessage = null;

        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder.getUniqueId());

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setBalance(account.getBalance() - amount);
            balance = account.getBalance();
            type = EconomyResponse.ResponseType.SUCCESS;
        } else {
            amount = 0;
            balance = 0;
            type = EconomyResponse.ResponseType.FAILURE;
            errorMessage = "That player could not be found.";
        }

        return new EconomyResponse(amount, balance, type, errorMessage);
    }

    @Override
    public EconomyResponse deposit(String accountHolder, double amount) {
        if (amount < 1)
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Please enter an amount greater than or equal 1.");

        double balance;
        EconomyResponse.ResponseType type;
        String errorMessage = null;

        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setBalance(account.getBalance() + amount);
            balance = account.getBalance();
            type = EconomyResponse.ResponseType.SUCCESS;
        } else {
            amount = 0;
            balance = 0;
            type = EconomyResponse.ResponseType.FAILURE;
            errorMessage = "That account could not be found.";
        }

        return new EconomyResponse(amount, balance, type, errorMessage);
    }

    @Override
    public EconomyResponse deposit(UUID accountHolder, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "You cannot deposit negative funds.");
        }

        double balance;
        EconomyResponse.ResponseType type;
        String errorMessage = null;

        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setBalance(account.getBalance() + amount);
            balance = account.getBalance();
            type = EconomyResponse.ResponseType.SUCCESS;
        } else {
            amount = 0;
            balance = 0;
            type = EconomyResponse.ResponseType.FAILURE;
            errorMessage = "That account could not be found.";
        }

        return new EconomyResponse(amount, balance, type, errorMessage);
    }

    @Override
    public EconomyResponse deposit(OfflinePlayer accountHolder, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "You cannot deposit negative funds.");
        }

        double balance;
        EconomyResponse.ResponseType type;
        String errorMessage = null;

        Optional<Account> accountOptional = goldEconomy.getAccountCache().get(accountHolder.getUniqueId());

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setBalance(account.getBalance() + amount);
            balance = account.getBalance();
            type = EconomyResponse.ResponseType.SUCCESS;
        } else {
            amount = 0;
            balance = 0;
            type = EconomyResponse.ResponseType.FAILURE;
            errorMessage = "That account could not be found.";
        }

        return new EconomyResponse(amount, balance, type, errorMessage);
    }

    @Override
    public EconomyResponse transfer(UUID sender, UUID receiver, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "You cannot transfer negative funds.");
        }

        double balance;
        EconomyResponse.ResponseType type;
        String errorMessage = null;

        Optional<Account> senderAccountOptional = goldEconomy.getAccountCache().get(sender);
        Optional<Account> receiverAccountOptional = goldEconomy.getAccountCache().get(receiver);

        if (senderAccountOptional.isPresent() && receiverAccountOptional.isPresent()) {
            Account senderAccount = senderAccountOptional.get();
            Account receiverAccount = receiverAccountOptional.get();

            senderAccount.setBalance(senderAccount.getBalance() - amount);
            receiverAccount.setBalance(receiverAccount.getBalance() + amount);
            balance = senderAccount.getBalance();
            type = EconomyResponse.ResponseType.SUCCESS;
        } else {
            amount = 0;
            balance = 0;
            type = EconomyResponse.ResponseType.FAILURE;
            errorMessage = "That account could not be found.";
        }

        return new EconomyResponse(amount, balance, type, errorMessage);
    }

    @Override
    public boolean hasAccount(String accountHolder) {
        return goldEconomy.getAccountCache().get(accountHolder).isPresent();
    }

    @Override
    public boolean hasAccount(UUID accountHolder) {
        return goldEconomy.getAccountCache().get(accountHolder).isPresent();
    }

    @Override
    public boolean hasAccount(OfflinePlayer accountHolder) {
        return goldEconomy.getAccountCache().get(accountHolder.getUniqueId()).isPresent();
    }

//    @Override
//    public Account getAccount(String accountHolder) {
//        return Account.getAccount(accountHolder);
//    }
//
//    @Override
//    public Account getAccount(UUID accountHolder) {
//        return Account.getAccount(accountHolder);
//    }
//
//    @Override
//    public Account getAccount(OfflinePlayer accountHolder) {
//        return Account.getAccount(accountHolder.getUniqueId());
//    }

    @Override
    public boolean createAccount(String accountHolder) {
        return false;
    }

    @Override
    public boolean createAccount(UUID accountHolder) {
        return false;
    }

    @Override
    public boolean createAccount(UUID accountHolderId, String accountHolder) {
        return false;
    }

    @Override
    public boolean createAccount(OfflinePlayer accountHolder) {
        return false;
    }

    @Override
    public boolean deleteAccount(String accountHolder) {
        return false;
    }

    @Override
    public boolean deleteAccount(UUID accountHolder) {
        return false;
    }

    @Override
    public boolean deleteAccount(OfflinePlayer accountHolder) {
        return false;
    }

    @Override
    public String format(double amount) {
        return formatAmount(amount);
    }

    @Override
    public String currencyNameSingular() {
        return "dollar";
    }

    @Override
    public String currencyNamePlural() {
        return "dollars";
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    private String formatAmount(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        String formatted = formatter.format(amount);

        if (formatted.endsWith("."))
            formatted = formatted.substring(0, formatted.length() - 1);

        return formatted;
    }
}