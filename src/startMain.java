import view.AdminFrame;
import view.LoginFrame;


public class startMain {
    public static void main(String[] args) {
        try {
            new LoginFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
