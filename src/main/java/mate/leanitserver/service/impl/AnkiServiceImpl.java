package mate.leanitserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.leanitserver.dto.anki.external.AnkiModelsDto;
import mate.leanitserver.dto.anki.internal.AnkiRequestDto;
import mate.leanitserver.dto.anki.internal.AnkiResponseDto;
import mate.leanitserver.exception.AnkiConnectException;
import mate.leanitserver.exception.EntityNotFoundException;
import mate.leanitserver.mapper.AnkiMapper;
import mate.leanitserver.model.AnkiCard;
import mate.leanitserver.model.Video;
import mate.leanitserver.repository.AnkiRepository;
import mate.leanitserver.repository.VideoRepository;
import mate.leanitserver.service.AnkiService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnkiServiceImpl implements AnkiService {
    private static final String ANKI_CONNECT_URL = "http://localhost:8765";
    private static final String CARD_ADD_JSON_REQUEST = "{"
            + "\"action\": \"addNote\","
            + "\"version\": 6,"
            + "\"params\": {"
            + "\"note\": {"
            + "\"deckName\": \"%s\","
            + "\"modelName\": \"%s\","
            + "\"fields\": {"
            + "\"Front\": \"%s\","
            + "\"Back\": \"%s\""
            + "},"
            + "\"tags\": []"
            + "}"
            + "}"
            + "}";
    private static final String CREATE_DECK_JSON_REQUEST = "{"
            + "\"action\": \"createDeck\","
            + "\"version\": 6,"
            + "\"params\": {"
            + "\"deck\": \"%s\""
            + "}"
            + "}";
    private static final String MODEL_NAMES_JSON_REQUEST = "{"
            + "\"action\": \"modelNames\","
            + "\"version\": 6"
            + "}";
    private static final String NOT_FOUND_VIDEO_EXCEPTION = "Can't find video by id: %d";
    private static final String NOT_FOUND_ANKI_CARD_EXCEPTION = "Can't find anki card by id: %d";
    private static final String NOT_CONNECT_ANKI_ERROR = "Can't connect to anki";
    private final AnkiRepository ankiRepository;
    private final AnkiMapper ankiMapper;
    private final VideoRepository videoRepository;

    @Transactional
    @Override
    public void addCardToDeck(Long id, String deckName) {
        List<String> modelNames = checkModelNames();
        if (modelNames.isEmpty()) {
            throw new EntityNotFoundException("No models found in Anki.");
        }
        AnkiCard ankiCard = ankiRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format(NOT_FOUND_ANKI_CARD_EXCEPTION, id))
        );
        String modelName = modelNames.get(1);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ANKI_CONNECT_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.format(
                        CARD_ADD_JSON_REQUEST,
                        deckName,
                        modelName,
                        ankiCard.getFront(),
                        ankiCard.getBack()))
                )
                .build();
        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new AnkiConnectException(NOT_CONNECT_ANKI_ERROR, e);
        }
    }

    @Transactional
    @Override
    public AnkiResponseDto save(AnkiRequestDto requestDto) {
        AnkiCard ankiCard = ankiMapper.toModel(requestDto);
        Video video = videoRepository.findById(requestDto.getVideoId()).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format(NOT_FOUND_VIDEO_EXCEPTION, requestDto.getVideoId()))
        );
        ankiCard = ankiRepository.save(ankiCard);
        video.getAnkiCards().add(ankiCard);
        videoRepository.save(video);
        return ankiMapper.toDto(ankiCard);
    }

    @Transactional
    @Override
    public AnkiResponseDto update(Long id, AnkiRequestDto requestDto) {
        AnkiCard ankiCard = ankiRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format(NOT_FOUND_ANKI_CARD_EXCEPTION, id))
        );
        ankiMapper.ankiUpdateFromDto(ankiCard, requestDto);
        return ankiMapper.toDto(ankiRepository.save(ankiCard));
    }

    @Override
    public List<AnkiResponseDto> findAll(Pageable pageable) {
        return ankiRepository.findAll(pageable)
                .map(ankiMapper::toDto)
                .toList();
    }

    @Override
    public AnkiResponseDto findById(Long id) {
        AnkiCard ankiCard = ankiRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format(NOT_FOUND_ANKI_CARD_EXCEPTION, id))
        );
        return ankiMapper.toDto(ankiCard);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        AnkiCard ankiCard = ankiRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format(NOT_FOUND_ANKI_CARD_EXCEPTION, id))
        );
        ankiRepository.delete(ankiCard);
    }

    public List<String> checkModelNames() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ANKI_CONNECT_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(MODEL_NAMES_JSON_REQUEST))
                .build();
        try {
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            AnkiModelsDto modelNamesResponse =
                    objectMapper.readValue(response.body(), AnkiModelsDto.class);
            return modelNamesResponse.getResult();
        } catch (IOException | InterruptedException e) {
            throw new AnkiConnectException(NOT_CONNECT_ANKI_ERROR, e);
        }
    }
}
