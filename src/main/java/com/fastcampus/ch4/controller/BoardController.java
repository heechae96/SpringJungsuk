package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.modify(boardDto);   // update

            if(rowCnt!=1)
                throw new Exception("Modify failed");

            rattr.addFlashAttribute("msg", "Modify OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);   // 내용을 살려두기 위해서 -> 나중에 사용 할 수도 있기 때문
            m.addAttribute("msg", "Modify Error");
            return "board";
        }
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String) session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.write(boardDto);   // insert

            if(rowCnt!=1)
                throw new Exception("Write failed");

            rattr.addFlashAttribute("msg", "Write OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);   // 내용을 살려두기 위해서 -> 나중에 사용 할 수도 있기 때문
            m.addAttribute("msg", "Write Error");
            return "board";
        }
    }

    @GetMapping("/write")
    public String wirte(Model m){
        m.addAttribute("mode", "new");
        return "board"; // 읽기와 쓰기에 사용하는데, 쓰기에 사용할때는 mode = new
    }

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        try {
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);

            int rowCnt = boardService.remove(bno, writer);

            if (rowCnt != 1)
                throw new Exception("board remove error");

            // RedirectAttributes는 session을 이용해서 1번쓰고 지움.
            // reload할때마다 뜨는걸 방지!
            // session에 부담이 적음
//            m.addAttribute("msg", "Delete OK");
            rattr.addFlashAttribute("msg", "Delete OK");
        } catch (Exception e) {
            e.printStackTrace();
//            m.addAttribute("msg", "Delete Error");
            rattr.addFlashAttribute("msg", "Delete Error");
        }

        return "redirect:/board/list";  // 모델에 담아서 보내면 자동으로 ?page=n&pageSize=n이 붙는다
    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m) {
        try {
            BoardDto boardDto = boardService.read(bno);
//            m.addAttribute("boardDto", boardDto);   // 아래 문장과 동일
            m.addAttribute(boardDto);   // 이름을 생략하면 타입의 첫글자를 소문자로 바꾼것이 적용
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board";
    }

    @GetMapping("/list")
    public String list(Integer page, Integer pageSize, Model m, HttpServletRequest request) {
        if (!loginCheck(request))
            return "redirect:/login/login?toURL=" + request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

        if (page == null)
            page = 1;
        if (pageSize == null)
            pageSize = 10;

        try {
            int totalCnt = boardService.getCount();
            PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);

            Map map = new HashMap();
            map.put("offset", (page - 1) * pageSize);
            map.put("pageSize", pageSize);

            List<BoardDto> list = boardService.getPage(map);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id") != null;
    }
}