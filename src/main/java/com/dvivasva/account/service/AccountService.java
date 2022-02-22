package com.dvivasva.account.service;

import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.repository.AccountRepository;
import com.dvivasva.account.utils.AccountUtil;
import com.dvivasva.account.webclient.CardWebClient;
import com.dvivasva.account.webclient.CustomerWebClient;
import com.dvivasva.account.webclient.dto.CardDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {

	 private  final AccountRepository accountRepository;
	 private final ReactiveMongoTemplate reactiveMongoTemplate;
	 private final static Logger logger= LoggerFactory.getLogger(AccountService.class);

	public Flux<AccountDto> read(){
		return accountRepository.findAll().map(AccountUtil::entityToDto);
	}

	public Mono<AccountDto> create(Mono<AccountDto> accountDtoMono){
		  logger.info("inside methode create");
		  CustomerWebClient customerWebClient= new CustomerWebClient();
		  accountDtoMono.map(p->{
			  var customerMono = customerWebClient.details(p.getCustomerId());
			  customerMono.switchIfEmpty(Mono.error(new ClassNotFoundException("no exist account")));

			  //Is Person
				  //verify qty number account
				  //findAllAccountsByCustomerId // ya no puede crear cuenta

			  //Is Enterprise


			  return p;
		  });
		  return  accountDtoMono.map(AccountUtil::dtoToEntity)
				  .flatMap(accountRepository::insert)
				  .map(AccountUtil::entityToDto);

	  }

	public Mono<AccountDto> update(Mono<AccountDto> accountDtoMono, String id){
		return accountRepository.findById(id)
				.flatMap(p->accountDtoMono.map(AccountUtil::dtoToEntity)
						.doOnNext(e->e.setId(id)))
				.flatMap(reactiveMongoTemplate::save)
				.map(AccountUtil::entityToDto);

	}
	public Mono<Void> delete(String id){
		return accountRepository.deleteById(id);
	}

	public Mono<AccountDto> findById(String id){
		return accountRepository.findById(id).map(AccountUtil::entityToDto);
	}

	/*public Mono<AccountDto> getBalance(String cardNumber){
		CardWebClient cardWebClient= new CardWebClient();
		var val=cardWebClient.findByNumber(cardNumber)
				.map(e->{
					var getAccounts=Flux.fromIterable(e.getConnectTo()).log();
					var account= getAccounts.flatMap(i -> Flux.from(accountRepository.findById(i)));

					return account.take(1).next().map(p-> p);

				});


	}*/

	/*
	public Flux<AccountDto> findAllAccountsByCustomerId(String customerId) {
		logger.info("get accounts by  customer");
		  return this.accountRepository.findAll()
				  .filter(document->document.getCustomerId().equals(customerId)).map(AccountUtil::entityToDto);
	}*/

}
