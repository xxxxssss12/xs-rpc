package name.xs.rpc.test.serialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xs
 * @CreateTime 2020/1/10 16:49
 */
public class TestBeanB implements Serializable {

    private List<String> lista = new ArrayList<>();

    private Map<TestEnumA, TestBeanA> listb = new HashMap<>();

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }

    public Map<TestEnumA, TestBeanA> getListb() {
        return listb;
    }

    public void setListb(Map<TestEnumA, TestBeanA> listb) {
        this.listb = listb;
    }
}
