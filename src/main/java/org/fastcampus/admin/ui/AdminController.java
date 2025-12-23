package org.fastcampus.admin.ui;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.fastcampus.admin.ui.dto.GetTableListResponse;
import org.fastcampus.admin.ui.dto.users.GetUserTableRequestDto;
import org.fastcampus.admin.ui.dto.users.GetUserTableResponseDto;
import org.fastcampus.admin.ui.query.AdminTableQueryRepository;
import org.fastcampus.admin.ui.query.UserStatsQueryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserStatsQueryRepository userStatsQueryRepository;
    private final AdminTableQueryRepository adminTableQueryRepository;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        mv.addObject("result", userStatsQueryRepository.getDailyRegisterUserStats(7));
        return mv;
    }

    @GetMapping("/users")
    public ModelAndView users(GetUserTableRequestDto dto) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("users");

        GetTableListResponse<GetUserTableResponseDto> result = adminTableQueryRepository.getUserTableData(dto);
        mv.addObject("requestDto", dto);
        mv.addObject("userList", result.getTableData());
        mv.addObject("totalCount", result.getTotalCount());
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
}
