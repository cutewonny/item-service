package hello.itemService.domain.item;

//import static org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class itemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();


    //테스트 하나하나 할때마다 해준다
    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("itemA", 5000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());

        //검증
        Assertions.assertThat(findItem).isEqualTo(savedItem);
     }
//    @Test
//    void findById(){
//        //given
//
//        //when
//
//        //then
//
//    }

    @Test
    void findAll(){
        //given
        Item item1 = new Item("item1", 1000, 3);
        Item item2 = new Item("item2", 2000, 2);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(item1, item2);
//        Assertions.from(item1).andThen(item1).apply(item2);
//        org.junit.jupiter.api.Assertions.assertEquals(result);
    }
    @Test
    void updateItem(){
        //given
        Item item = new Item("item1", 10000, 10);
        System.out.println(item);

        Item saveItem = itemRepository.save(item);
        Long itemId = saveItem.getId();

        //when
        Item updateParam = new Item("ittt", 555, 5);
        System.out.println(updateParam);
        itemRepository.update(itemId, updateParam);

        //then
        Item findItem = itemRepository.findById(itemId);
        System.out.println(findItem);
        System.out.println("findItem.getItemName() = " + findItem.getItemName());
        System.out.println("findItem.getPrice() = " + findItem.getPrice());
        System.out.println("findItem.getQuantity() = " + findItem.getQuantity());

        System.out.println("updateParam.getItemName() = " + updateParam.getItemName());
        System.out.println("updateParam.getPrice() = " + updateParam.getPrice());
        System.out.println("updateParam.getQuantity() = " + updateParam.getQuantity());

        System.out.println("item.getItemName() = " + item.getItemName());
        System.out.println("item.getPrice() = " + item.getPrice());
        System.out.println("item.getQuantity() = " + item.getQuantity());
//        Assertions.assertThat(findItem).isEqualTo(updateParam);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

//        assertThat(findItem.getItemName()).isNotEqualTo(item.getItemName());
//        assertThat(findItem.getPrice()).isNotEqualTo(item.getPrice());
//        assertThat(findItem.getQuantity()).isNotEqualTo(item.getQuantity());
    }
}