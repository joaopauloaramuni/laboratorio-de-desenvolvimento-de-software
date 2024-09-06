package com.example.RestAPI.service;
import com.example.RestAPI.model.ReleasesEntity;
import com.example.RestAPI.repository.NewsRepository;
import com.example.RestAPI.model.NewsEntity;
import com.example.RestAPI.repository.ReleasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class IBGEService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ReleasesRepository releasesRepository;

    public String consultarUrl(String apiURL){
        String dados = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiURL, String.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            dados = responseEntity.getBody();
            System.out.println(dados);
        }else{
            dados = "Falha ao obter dados, Código de status " + responseEntity.getStatusCode();
        }

        return dados;
    }

    public String consultarNoticias() {
        String news = consultarUrl("https://servicodados.ibge.gov.br/api/v3/noticias/?tipo=noticia");
        inserirNews(news);
        return news;
    }

    private void inserirNews(String news) {
        NewsEntity objNews = new NewsEntity();
        objNews.setNoticias(news);
        inserirNewsRepository(objNews);
    }

    public String consultarReleases() {
        String releases = consultarUrl("https://servicodados.ibge.gov.br/api/v3/noticias/?tipo=noticia");
        inserirReleases(releases);
        return releases;
    }

    private void inserirReleases(String releases) {
        ReleasesEntity objReleases = new ReleasesEntity();
        objReleases.setReleases(releases);
        inserirReleasesRepository(objReleases);
    }

    public String consultarNoticiasEReleases() {
        return consultarUrl("https://servicodados.ibge.gov.br/api/v3/noticias");
    }

    public NewsEntity inserirNewsRepository(NewsEntity news) {
        return newsRepository.save(news);
    }

    public ReleasesEntity inserirReleasesRepository(ReleasesEntity releases) {
        return releasesRepository.save(releases);
    }

}
