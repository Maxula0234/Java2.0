package org.levelup.chat.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_channels")
public class UserChannel {
}
