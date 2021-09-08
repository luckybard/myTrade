package com.myTrade.unitTests.services;

//@ExtendWith(MockitoExtension.class)
//class MessageServiceUnitTest {
//
//    @Mock
//    MessageRepository messageRepository;
//
//    @InjectMocks
//    MessageService messageService;
//
//    private MessageDto message = new MessageDto();
//
//    @BeforeEach
//    public void setUpEntity(){
//        message.setId(1L);
//    }
//
////    @Test
////    void whenMessageIdIsProvided_thenReturnMessageEntity() {
////        //given
////        given(messageRepository.getById(message.getId())).willReturn((message));
////        //when
////        MessageEntity result = messageRepository.getById(message.getId());
////        //then
////        assertThat(result.getClass()).isEqualTo(MessageEntity.class);
////        assertThat(result ).isNotNull();
////    }
//
//    @Test
//    void saveMessageWhenMessageEntityIsProvided_thenMessageShouldBeSavedIntoDatabase() {
//        //given
//        //when
//        messageService.saveMessage(message);
//        //then
//        verify(messageRepository).save(message);
//    }
//}