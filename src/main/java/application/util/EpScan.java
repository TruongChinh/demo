package application.util;

public interface EpScan {
    void scan(String url, int scanTime);

    void deleteScanResult();
}
