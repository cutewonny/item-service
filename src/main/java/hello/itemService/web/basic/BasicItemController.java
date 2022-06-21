package hello.itemService.web.basic;

import hello.itemService.domain.item.Item;
import hello.itemService.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    /*
    @RequiredArgsConstructor == 생성자 주입
    1) 생성자 <-- 이것
    2) Setter
    3) 필드 + @Autowired
    https://velog.io/@developerjun0615/Spring-RequiredArgsConstructor-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%9C-%EC%83%9D%EC%84%B1%EC%9E%90-%EC%A3%BC%EC%9E%85
     */

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "basic/editForm";
    }

/*
RedirectAttributes
RedirectAttributes를 사용하면 URL인코딩도 해주고, pathVariable 쿼리 파라미터까지 처리해준다.
pathVariable 바인딩: {itemId}
나머지는 쿼리 파라미터로 처리 : ?status=true
 */



    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());//변수, url 인코딩도 해준다
        redirectAttributes.addAttribute("status", true);// 파라미터 키, 값
        return "redirct:/basic/item/{itemId}";//status 는 쿼리 파라미터로 전달됨
        //http://localhost:8080/basic/items/3?status=true
    }



    @PostConstruct
    public void init(){
        /*
        @PostConstruct
        종속성 주입이 완료된 후 실행되어야 하는 메서드에 사용된다.
         이 어노테이션은 다른 리소스에서 호출되지 않아도 수행된다.
         */
        itemRepository.save(new Item("AAAA", 10000, 10));
        itemRepository.save(new Item("BBBB", 20000, 20));
    }
}
