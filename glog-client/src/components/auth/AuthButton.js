import { useState } from "react";
import styled, { css } from "styled-components";
import { actionCreators as userActions } from '../../store/reducer/users';
import { history } from '../../store/configureStore';
import { useDispatch } from 'react-redux';
//import ErrorMessage from '../layout/ErrorMessage';

function AuthButton({ isLink, defaultType, fullWidth, _onClick, _disabled, ...rest }) {
  const dispatch = useDispatch();

  if (isLink) {
    return <LinkStyled defaultType={defaultType} {...rest} />;
  }
  return (
    <>
      <ButtonStyled
        type="button"
        fullWidth={fullWidth}
        disabled={_disabled}
        onClick={_onClick}
        {...rest}
      />
    </>
  );
}

const commonStyled = css`
  ${(props) =>
    props.fullWidth &&
    css`
      width: 100%;
    `}
  height: 50px;
  line-height: 20px;
  font-size: 18px;
  border: none;
  border-radius: 1.5rem;
  cursor: pointer;
  color: #fff;
  background: #a680d2;
  &:hover {
    background: #8062a3;
  }
`;

const LinkStyled = styled.a`
  ${commonStyled}
  text-decoration: none;
  font-size: 16px;
  padding: 5px 10px;
  ${(props) =>
    props.defaultType &&
    css`
      background: #797979;
      &:hover {
        background: #424242;
      }
    `}
`;

const ButtonStyled = styled.button`
  ${commonStyled}
`;

export default AuthButton;
