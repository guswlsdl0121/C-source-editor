package Model;

public class KeywordModel {
    private String[] KeyWord = {"#include","#define","void", "struct","class","union", "enum","if","for","while","return","false","true","sizeof", "public","private","protected","else","typedef","#pragma", "fwrite","fread","warning","disable","do","static","default", "const","switch","case","break","continue","#undef","#if", "#elseif","#else","#endif","#ifndef","#ifdef","inline","extern", "#import","#using","#error","#line","once"};

    public String[] getKeyword()
    {
        return KeyWord;
    }
}