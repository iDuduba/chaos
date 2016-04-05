package wang.laic.chaos.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;

import wang.laic.chaos.model.User;
import wang.laic.chaos.service.UserService;

//@RestController
@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    private UserService userService;

    @RequestMapping
    public ModelAndView getAll(User user) {
    	logger.debug("name:{},pass:{},page:{},page.size:{}", user.getUsername(), user.getPassword(), user.getPage(), user.getRows());
    	
        ModelAndView result = new ModelAndView("userlist");
        List<User> userlist = userService.getAll(user);
        result.addObject("pageInfo", new PageInfo<User>(userlist));
        result.addObject("queryParam", user);
        result.addObject("page", user.getPage());
        result.addObject("rows", user.getRows());
        return result;
    }

    @RequestMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView result = new ModelAndView("view");
        result.addObject("user", new User());
        return result;
    }
    
    @RequestMapping(value = "/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView("view");
        User user = userService.getById(id);
        result.addObject("user", user);
        return result;    	
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, RedirectAttributes ra) {
        ModelAndView result = new ModelAndView("redirect:/users");
        userService.deleteByPrimaryKey(id);
        ra.addFlashAttribute("msg", "删除成功!");
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(User user) {
        ModelAndView result = new ModelAndView("view");
        String msg = user.getId() == null ? "新增成功!" : "更新成功!";
        userService.save(user);
        result.addObject("user", user);
        result.addObject("msg", msg);
        return result;
    }
}
