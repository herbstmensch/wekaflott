import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import de.timherbst.wau.domain.Turner;
import de.timherbst.wau.domain.WettkampfTag;
 
public class CollectionsDemo {
 
    public static void main(String[] args) {
 
        // Use Java Collections to create the List.
 
        // Now add observability by wrapping it with ObservableList.
    ObservableList<Turner> observableList = FXCollections.observableList(WettkampfTag.get().getTurner());
        observableList.addListener(new ListChangeListener() {
 
            @Override
            public void onChanged(ListChangeListener.Change change) {
                System.out.println("Detected a change! ");
            }
        });
 
        // Changes to the observableList WILL be reported.
        // This line will print out "Detected a change!"
        observableList.add(new Turner());
 
        // Changes to the underlying list will NOT be reported
        // Nothing will be printed as a result of the next line.
        WettkampfTag.get().getTurner().add(new Turner());
        WettkampfTag.get().addTurner(new Turner());

        System.out.println("Size: "+observableList.size());
        System.out.println("Size: "+WettkampfTag.get().getTurner().size());
 
    }
}