package com.myTrade.utility;

import com.myTrade.entities.AdEntity;
import com.myTrade.entities.ConversationEntity;
import com.myTrade.entities.MessageEntity;
import com.myTrade.entities.UserEntity;
import com.myTrade.utility.pojo.AdCategory;
import com.myTrade.utility.pojo.City;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.myTrade.utility.pojo.AdCategory.*;
import static com.myTrade.utility.pojo.City.*;
import static com.myTrade.utility.pojo.UserRole.USER;

public class TestUtility {
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int ONE_ADDITIONAL_AD = 1;
    public static final int ONE_ADDITIONAL_MESSAGE = 1;
    public static final Long ONE_ADDITIONAL_CONVERSATION = 1L;

    public static void setUpSecurityContext(UserEntity user){
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }

    public static void setUpDefaultUserEntity(UserEntity userEntity) {
        userEntity.setId(1L);
        userEntity.setUsername("mike@mike.mike");
        userEntity.setPassword("$2a$10$SPdo6xnJWx7GrjKLkLXAGerIclvMnfjOy3rx3.VslhXhzsfq9nud.");
        userEntity.setEmail("mike@mike.mike");
        userEntity.setRole(USER);
        userEntity.setAdEntityList(setUpAdEntityList());
        userEntity.setFavouriteAdEntityList(setUpFavouriteAdEntityList());
        userEntity.setHighlightPoints(5);
    }

    private static List<AdEntity> setUpAdEntityList() {
        AdEntity adEntity1 = new AdEntity();
        adEntity1.setId(10L);
        adEntity1.setOwnerUsername("mike@mike.mike");
        adEntity1.setAdCategory(AdCategory.BOOKS);
        adEntity1.setTitle("The Godfather");
        adEntity1.setDescription("The Godfather is a crime novel by American author Mario Puzo. Originally published in 1969 by G. P. Putnam's Sons, the novel details the story of a fictional Mafia family in New York City (and Long Island), headed by Vito Corleone, the Godfather. The novel covers the years 1945 to 1955 and includes the back story of Vito Corleone from early childhood to adulthood.");
        adEntity1.setPrice(40.00);
        adEntity1.setCity(City.WARSAW);
        adEntity1.setIsActive(Boolean.TRUE);
        adEntity1.setExpirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity1.setCreatedDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity1.setRefreshDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity1.setModifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27));

        AdEntity adEntity2 = new AdEntity();
        adEntity2.setId(11L);
        adEntity2.setOwnerUsername("mike@mike.mike");
        adEntity2.setAdCategory(AdCategory.FURNITURE);
        adEntity2.setTitle("Antique chair");
        adEntity2.setDescription("Very comfortable chair");
        adEntity2.setPrice(60.00);
        adEntity2.setCity(City.WARSAW);
        adEntity2.setIsActive(Boolean.TRUE);
        adEntity2.setExpirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity2.setCreatedDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity2.setRefreshDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity2.setModifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27));

        return List.of(adEntity1, adEntity2);
    }

    private static List<AdEntity> setUpFavouriteAdEntityList() {
        AdEntity adEntity1 = new AdEntity();
        adEntity1.setId(6L);
        adEntity1.setOwnerUsername("john@john.com");
        adEntity1.setAdCategory(AdCategory.BOOKS);
        adEntity1.setTitle("The Lord of the Rings");
        adEntity1.setDescription("The Fellowship of the Ring is the first of three volumes of the epic novel The Lord of the Rings by the English author J. R. R. Tolkien. It is followed by The Two Towers and The Return of the King. It takes place in the fictional universe of Middle-earth. It was originally published on 29 July 1954 in the United Kingdom.");
        adEntity1.setPrice(15.00);
        adEntity1.setCity(City.WARSAW);
        adEntity1.setIsActive(Boolean.TRUE);
        adEntity1.setExpirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity1.setCreatedDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity1.setRefreshDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity1.setModifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27));

        AdEntity adEntity2 = new AdEntity();
        adEntity2.setId(7L);
        adEntity2.setOwnerUsername("john@john.com");
        adEntity2.setAdCategory(AdCategory.BOOKS);
        adEntity2.setTitle("The Lord of the Rings");
        adEntity1.setDescription("The Two Towers is the second volume of J. R. R. Tolkien's high fantasy novel The Lord of the Rings. It is preceded by The Fellowship of the Ring and followed by The Return of the King.");
        adEntity2.setPrice(15.00);
        adEntity2.setCity(City.WARSAW);
        adEntity2.setIsActive(Boolean.TRUE);
        adEntity2.setExpirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity2.setCreatedDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity2.setRefreshDate(LocalDate.ofEpochDay(2021 - 11 - 27));
        adEntity2.setModifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27));

        return List.of(adEntity1, adEntity2);
    }

    public static List<AdEntity> getAdEntityList(){
        List<AdEntity> adEntityList = List.of(
                AdEntity.builder().id(1L)
                        .adCategory(FURNITURE)
                        .title("Corner wardrobe mirror door")
                        .description("As titled. In great condition, like new. Very easy to assemble. Mirrored door, inside has 3 shelves, big one on top and two smaller ones down the side and a clothes rail of course.")
                        .city(LONDON)
                        .price(75D)
                        .ownerUsername("brad@brad.brad")
                        .countView(5L)
                        .isActive(true)
                        .createdDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .modifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .refreshDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .expirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .build(),
                AdEntity.builder().id(2L)
                        .adCategory(FURNITURE)
                        .title("Walnut wood table and 8 leather chairs")
                        .description("This set consists of a second-hand walnut wood dining table and 8 brown leather chairs in excellent condition. The dining table is adjustable and can seat 8 or the middle panel can slide in and become smaller and seat 6. The set has an antique look, stately appeal, and a graceful appearance. There are no major damages on the table or any of the chairs outside of normal wear and tear.")
                        .city(LONDON)
                        .price(600D)
                        .ownerUsername("brad@brad.brad")
                        .countView(10L)
                        .isActive(true)
                        .createdDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .modifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .refreshDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .expirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .build(),
                AdEntity.builder().id(3L)
                        .adCategory(FURNITURE)
                        .title("Side tables")
                        .description("2 side tables in metal. They have a cobber like finish which patinas beautifully. Height: 56 cm, diameter: 39 cm. 3 legs £30 each")
                        .city(LONDON)
                        .price(60D)
                        .ownerUsername("brad@brad.brad")
                        .countView(15L)
                        .isActive(true)
                        .createdDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .modifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .refreshDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .expirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .build(),
                AdEntity.builder().id(4L)
                        .adCategory(CLOTHES)
                        .title("Disney Princess Cinderella Pyjamas Pajamas Girls Toddlers")
                        .description("Disney Princess Cinderella Pyjamas Pajamas Pjs Girls Toddlers 18 months to 5 Years Official  Cinderella Pyjama Set (Secret Princess) DESIGN: Each pair is a two piece set with a long sleeved top and full length trousers Cotton Blend Machine Washable, Fastening: Pull On")
                        .city(PARIS)
                        .price(75D)
                        .ownerUsername("kate@kate.kate")
                        .countView(5L)
                        .isActive(false)
                        .createdDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .modifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .refreshDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .expirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .build(),
                AdEntity.builder().id(5L)
                        .adCategory(CLOTHES)
                        .title("Ted Baker Kikiie Wrap Coat Pale Nude Pink Wool Cashmere")
                        .description("Formed from an opulent mix of wool and cashmere, the Kikiie coat will cocoon you in a layer of luxury. It boasts a high standing neckline and a waist-cinching belt to simultaneously banish the elements and accentuate your figure.")
                        .city(PARIS)
                        .price(250D)
                        .ownerUsername("kate@kate.kate")
                        .countView(250L)
                        .isActive(false)
                        .createdDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .modifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .refreshDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .expirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .build(),
                AdEntity.builder().id(6L)
                        .adCategory(BOOKS)
                        .title("The Lord of the Rings")
                        .description("The Fellowship of the Ring is the first of three volumes of the epic novel The Lord of the Rings by the English author J. R. R. Tolkien. It is followed by The Two Towers and The Return of the King. It takes place in the fictional universe of Middle-earth. It was originally published on 29 July 1954 in the United Kingdom.")
                        .city(WARSAW)
                        .price(15D)
                        .ownerUsername("john@john.john")
                        .countView(300L)
                        .isActive(true)
                        .createdDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .modifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .refreshDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .expirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .build(),
                AdEntity.builder().id(7L)
                        .adCategory(BOOKS)
                        .title("The Lord of the Rings")
                        .description("The Two Towers is the second volume of J. R. R. Tolkien's high fantasy novel The Lord of the Rings. It is preceded by The Fellowship of the Ring and followed by The Return of the King.")
                        .city(WARSAW)
                        .price(15D)
                        .ownerUsername("john@john.john")
                        .countView(310L)
                        .isActive(true)
                        .createdDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .modifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .refreshDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .expirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .build(),
                AdEntity.builder().id(8L)
                        .adCategory(BOOKS)
                        .title("The Lord of the Rings")
                        .description("The Return of the King is the third and final volume of J. R. R. Tolkien's The Lord of the Rings, following The Fellowship of the Ring and The Two Towers. It was published in 1955. The story begins in the kingdom of Gondor, which is soon to be attacked by the Dark Lord Sauron.")
                        .city(WARSAW)
                        .price(15D)
                        .ownerUsername("john@john.john")
                        .countView(320L)
                        .isActive(true)
                        .createdDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .modifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .refreshDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .expirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                        .build());
        return adEntityList;
    }

    public static List<ConversationEntity> getConversationEntityList(){
        return List.of(
                ConversationEntity.builder()
                        .id(1L)
                        .senderUsername("mike@mike.mike")
                        .recipientUsername("brad@brad.brad")
                        .title("title")
                        .messageList(new ArrayList<>())
                        .build(),
                ConversationEntity.builder()
                        .id(1L)
                        .senderUsername("mike@mike.mike")
                        .recipientUsername("kate@kate.kate")
                        .title("title")
                        .messageList(new ArrayList<>())
                        .build(),
                ConversationEntity.builder()
                        .id(1L)
                        .senderUsername("john@john.john")
                        .recipientUsername("mike@mike.mike")
                        .title("title")
                        .messageList(new ArrayList<>())
                        .build());
    }

    public static AdEntity getAdEntity(){
        return AdEntity.builder().id(1L)
                .adCategory(FURNITURE)
                .title("Corner wardrobe mirror door")
                .description("As titled. In great condition, like new. Very easy to assemble. Mirrored door, inside has 3 shelves, big one on top and two smaller ones down the side and a clothes rail of course.")
                .city(LONDON)
                .price(75D)
                .ownerUsername("brad@brad.brad")
                .countView(5L)
                .isActive(true)
                .createdDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                .modifiedDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                .refreshDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                .expirationHighlightDate(LocalDate.ofEpochDay(2021 - 11 - 27))
                .build();
    }


    public static List<MessageEntity> setUpMessageEntityList() {
        List messages = new ArrayList<>();
        messages.add(new MessageEntity(1L, "user1", "text", LocalDateTime.now()));
        messages.add(new MessageEntity(2L, "user2", "text", LocalDateTime.now()));
        messages.add(new MessageEntity(3L, "user2", "text", LocalDateTime.now()));
        messages.add(new MessageEntity(4L, "user1", "text", LocalDateTime.now()));
        return messages;
    }
}
