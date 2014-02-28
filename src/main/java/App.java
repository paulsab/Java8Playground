import com.sabstuff.lambda.App.Address;
import com.sabstuff.lambda.App.AddressForm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: P.Sabatino (paul@qstartlabs.com)
 * Date: 2/26/14
 * Time: 9:59 PM
 */
public class App {


    public static void main(String[] args) {

        App a = new App();
        long start = System.currentTimeMillis();
        List<Address> list = generateAddressList(1000000);
        System.out.println("list generation = " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        List<AddressForm> forms = a.runParallel(list);
        System.out.println("Parallel = " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("\tform list size: "+forms.size());

        start = System.currentTimeMillis();
        forms = a.runSerial(list);
        System.out.println("Serial = "+(System.currentTimeMillis()-start)+"ms");
        System.out.println("\tform list size: "+forms.size());

        start = System.currentTimeMillis();
        forms = a.runParallel(list);
        System.out.println("Parallel2 = " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("\tform list size: "+forms.size());


    }


    public List<AddressForm> runSerial(List<Address> list) {

        List<AddressForm> forms = new ArrayList<AddressForm>();
        list.stream()
                .forEach(p -> forms.add(populateForm(p)));

        return forms;
    }

    public List<AddressForm> runParallel(List<Address> list) {

//        List<AddressForm> forms = new ArrayList<AddressForm>();

        List<AddressForm> forms =
        list.parallelStream().map(p->populateForm(p))
                .collect(Collectors.toList());

        return forms;
    }


    private AddressForm populateForm(Address a ) {
        return new AddressForm();
    }


    private static List<Address> generateAddressList(int n) {

        List<Address> list = new ArrayList<Address>();
        for (int i = 0; i < n; i++) {
            list.add(new Address());
        }

        return list;
    }
}
