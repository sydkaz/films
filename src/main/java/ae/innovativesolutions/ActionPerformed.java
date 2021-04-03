package ae.innovativesolutions;

enum Actions {
    CREATE,
    GETONE,
    GETALL
}

public class ActionPerformed {
    Actions action;
    public ActionPerformed(Actions action){
        this.action = action;
    }

    public Actions getAction() {
        return action;
    }
}
