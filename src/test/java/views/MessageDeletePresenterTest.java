package views;


import message_edit_delete_use_case.MessageDeleteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presenters.MessageDeletePresenter;
import static org.junit.jupiter.api.Assertions.*;

class MessageDeletePresenterTest {
    private MessageDeletePresenter presenter;

    @BeforeEach
    void setUp() {
        this.presenter = new MessageDeletePresenter();
    }

    @Test
    void prepareSuccessView() {
        MessageDeleteResponse response = new MessageDeleteResponse(4567, true, null);
        assertEquals(response, presenter.prepareSuccessView(response));
    }
}

