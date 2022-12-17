package Model;
/**
 * 키워드 색 지정을 위해 키워드명들을 담고있는 Model
 * @implNote  어디에서 들고온지는 보고서에 기술하였음
 * @author 송제용
 * @version 1.0
 */
public class KeywordModel {
    private final String[] KeyWord = {"#include","#define","struct","union", "enum","for","while","return","false","true",
            "sizeof","typedef","#pragma","do","static","default","const","switch","case","break",
            "continue","if","else","goto","register","extern","volatile"};

    public String[] getKeyword()
    {
        return KeyWord;
    }
}