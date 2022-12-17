package Model;
/**
 * 헤더파일 색 지정을 위한 헤더파일명 데이터를 담고있는 Model
 * @implNote  어디에서 들고온지는 보고서에 기술하였음
 * @author 송제용
 * @version 1.0
 */
public class HeaderModel{
    private final String[] Header = {"<assert.h>", "<complex.h>", "<conio.h>", "<corecrt.h>", "<corecrt_io.h>", "<corecrt_malloc.h>", "<corecrt_math.h>",
            "<corecrt_math_defines.h>", "<corecrt_memcpy_s.h>", "<corecrt_memory.h>", "<corecrt_search.h>", "<corecrt_share.h>",
            "<corecrt_startup.h>", "<corecrt_stdio_config.h>", "<corecrt_terminate.h>", "<corecrt_wconio.h>", "<corecrt_wctype.h>",
            "<corecrt_wdirect.h>", "<corecrt_wio.h>", "<corecrt_wprocess.h>", "<corecrt_wstdio.h>", "<corecrt_wstdlib.h>", "<corecrt_wstring.h>",
            "<corecrt_wtime.h>", "<crtdbg.h>", "<ctype.h>", "<direct.h>", "<dos.h>", "<errno.h>", "<fcntl.h>", "<fenv.h>", "<float.h>",
            "<fpieee.h>", "<inttypes.h>", "<io.h>", "<locale.h>", "<malloc.h>", "<math.h>", "<mbctype.h>", "<mbstring.h>", "<memory.h>",
            "<minmax.h>", "<new.h>", "<process.h>", "<safeint.h>", "<safeint_internal.h>", "<search.h>", "<share.h>", "<signal.h>", "<stddef.h>",
            "<stdio.h>", "<stdlib.h>", "<string.h>", "<tchar.h>", "<tgmath.h>", "<time.h>", "<uchar.h>", "<wchar.h>",
            "<wctype.h>", "<locking.h>", "<stat.h>", "<timeb.h>", "<types.h>", "<utime.h>"};

    public String[] getHeader()
    {
        return Header;
    }
}
