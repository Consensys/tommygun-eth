package net.consensys.tommygun.service;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import net.consensys.tommygun.api.fire.FireRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

@Service
@Slf4j
public class FireService {
  @Autowired private Web3j web3j;

  public UUID fire(final FireRequest fireRequest) {
    log.info("fire request initiated: {}", fireRequest.toString());
    final UUID taskID = UUID.randomUUID();
    return taskID;
  }
}
