package com.dvivasva.account.controller;


import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
	
private final AccountService accountService;
	
	@GetMapping
	public Flux<AccountDto>read() {
		return accountService.read();
	}

	@PostMapping
	public Mono<AccountDto> create(@RequestBody Mono<AccountDto> accountDtoMono) {
		return accountService.create(accountDtoMono);
	}
	@PutMapping("/{id}")
	public Mono<AccountDto> update(@RequestBody Mono<AccountDto> accountDtoMono, @PathVariable String id){
		return accountService.update(accountDtoMono,id);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable String id){
		return accountService.delete(id);
	}


	@GetMapping("/{id}")
	public Mono<AccountDto> findById(@PathVariable String id){
		return accountService.findById(id);
	}



}
