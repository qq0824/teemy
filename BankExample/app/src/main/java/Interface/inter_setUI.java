package Interface;

import android.content.Intent;
import android.view.View;

public interface inter_setUI {
    public void set_ID(int i);
    public void Choose(String a);
    public void set_intent(View v, int key);
    public void getResult(int requestCode, Intent data);
}
