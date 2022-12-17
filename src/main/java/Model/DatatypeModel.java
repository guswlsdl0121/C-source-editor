package Model;
/**
 * 데이터타입 색 지정을 위한 데이터타입 데이터를 담고있는 Model
 * @implNote  어디에서 들고온지는 보고서에 기술하였음
 * @author 송제용
 * @version 1.0
 */
public class DatatypeModel{
    private final String[] datatype = {"int","float", "char","long","short","unsigned","bool","double","signed","void"};

    public String[] getDatatype()
    {
        return datatype;
    }
}
