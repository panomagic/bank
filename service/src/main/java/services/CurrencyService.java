package services;

import beans.Currency;
import java.util.List;

public interface CurrencyService {
    Currency addCurrency(Currency currency);
    Currency getCurrencyByID(Integer id);
    boolean updateCurrency(Currency currency);
    boolean deleteCurrency(Currency currency);
    List<Currency> getAllCurrencies();
}